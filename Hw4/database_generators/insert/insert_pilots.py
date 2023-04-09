import random
import psycopg2
from faker import Faker
from constants import HOST, PORT, DATABASE, USER, PASSWORD, SPECIAL_CHARS
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
                insert_query = \
                    "INSERT INTO Pilots (first_name, last_name, nationality, date, driving_experience) VALUES "
                values = []
                for i in range(1000000):
                    first_name = fake.name()
                    first_name = "".join(c for c in first_name if c not in SPECIAL_CHARS).lower()
                    last_name = fake.name()
                    last_name = "".join(c for c in last_name if c not in SPECIAL_CHARS).lower()
                    nationality = fake.country()
                    nationality = "".join(c for c in nationality if c not in SPECIAL_CHARS).lower()
                    driving_experience = random.randint(3, 30)

                    d1 = datetime.strptime('1/1/2008 1:30 PM', '%m/%d/%Y %I:%M %p')
                    d2 = datetime.strptime('1/1/2009 4:50 AM', '%m/%d/%Y %I:%M %p')

                    date = random_date(d1, d2)

                    values.append(f"('{first_name}', '{last_name}', '{nationality}', '{date}', {driving_experience})")
                    if len(values) == 1000:
                        f.write(insert_query + ", ".join(values) + ";\n")
                        values = []
    except Exception as error:
        print(error)
    finally:
        if conn:
            cursor.close()
            conn.close()
