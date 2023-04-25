import random
import psycopg2
from faker import Faker
from constants import HOST, PORT, DATABASE, USER, PASSWORD, SPECIAL_CHARS, SPECIAL_CHARS_DESCRIPTION
from datetime import datetime, timedelta


def random_date(start, end):
    delta = end - start
    int_delta = (delta.days * 24 * 60 * 60) + delta.seconds
    random_second = random.randrange(int_delta)
    return start + timedelta(seconds=random_second)


def insert_data_user_profiles():
    conn = psycopg2.connect(
        host=HOST,
        port=PORT,
        database=DATABASE,
        user=USER,
        password=PASSWORD
    )

    try:
        with open("./queries/insert_user_profiles.sql", "w", encoding="utf-8") as f:
            fake = Faker()
            with conn.cursor() as cursor:
                cursor.execute("SELECT id from users")
                user_ids = [el[0] for el in cursor.fetchall()]
                insert_query = \
                    "INSERT INTO user_profiles (bio, birthday, gender, location, marital_status, user_id) VALUES "
                values = []
                for user_id in user_ids:

                    bio = fake.paragraph(nb_sentences=3)
                    bio = "".join(c for c in bio if c not in SPECIAL_CHARS_DESCRIPTION)
                    location = fake.country()
                    location = "".join(c for c in location if c not in SPECIAL_CHARS_DESCRIPTION)
                    d1 = datetime.strptime('1/1/1970 1:30 PM', '%m/%d/%Y %I:%M %p')
                    d2 = datetime.strptime('1/1/2002 4:50 AM', '%m/%d/%Y %I:%M %p')
                    birthday = random_date(d1, d2)
                    gender_list = ['male', 'female']
                    gender = random.choice(gender_list)
                    marital_status_list = ['single', 'married', 'divorced', 'separated', 'civil union',
                                           'domestic partnership']
                    marital_status = random.choice(marital_status_list)

                    values.append(f"('{bio}', '{birthday}', '{gender}', '{location}', '{marital_status}', {user_id})")
                    if len(values) == 1000:
                        f.write(insert_query + ", ".join(values) + ";\n")
                        values = []

    except Exception as error:
        print(error)
    finally:
        if conn:
            cursor.close()
            conn.close()
