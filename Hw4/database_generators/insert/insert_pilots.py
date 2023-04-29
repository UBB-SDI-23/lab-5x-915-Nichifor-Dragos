import random
import psycopg2
from faker import Faker
from constants import HOST, PORT, DATABASE, USER, PASSWORD, SPECIAL_CHARS, SPECIAL_CHARS_DESCRIPTION
from datetime import datetime, timedelta


def random_date(start, end):
    """
    This function will return a random datetime between two datetime
    objects.
    """
    delta = end - start
    int_delta = (delta.days * 24 * 60 * 60) + delta.seconds
    random_second = random.randrange(int_delta)
    return start + timedelta(seconds=random_second)


def insert_data_pilots():
    conn = psycopg2.connect(
        host=HOST,
        port=PORT,
        database=DATABASE,
        user=USER,
        password=PASSWORD
    )

    try:
        with open("./queries/insert_pilots.sql", "w", encoding="utf-8") as f:
            fake = Faker()
            with conn.cursor() as cursor:
                cursor.execute("SELECT id from users")
                user_ids = [el[0] for el in cursor.fetchall()]
                insert_query = \
                    "INSERT INTO Pilots (first_name, last_name, nationality, date, driving_experience, user_id) VALUES "
                values = []

                for i in range(1000000):
                    name = fake.name()
                    name = name.split()

                    first_name = name[0]
                    first_name = "".join(c for c in first_name if c not in SPECIAL_CHARS)

                    last_name = name[1]
                    last_name = "".join(c for c in last_name if c not in SPECIAL_CHARS)

                    nationality = fake.country()
                    nationality = "".join(c for c in nationality if c not in SPECIAL_CHARS_DESCRIPTION)

                    d1 = datetime.strptime('1/1/1970 1:30 PM', '%m/%d/%Y %I:%M %p')
                    d2 = datetime.strptime('1/1/2002 4:50 AM', '%m/%d/%Y %I:%M %p')
                    date = random_date(d1, d2)

                    driving_experience = random.randint(3, 25)

                    user_id = random.choice(user_ids)

                    values.append(
                        f"('{first_name}', '{last_name}', '{nationality}', '{date}', {driving_experience}, {user_id})")

                    if len(values) == 1000:
                        f.write(insert_query + ", ".join(values) + ";\n")
                        values = []
    except Exception as error:
        print(error)
    finally:
        if conn:
            cursor.close()
            conn.close()
