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


usernames = {}


def insert_data_users():
    conn = psycopg2.connect(
        host=HOST,
        port=PORT,
        database=DATABASE,
        user=USER,
        password=PASSWORD
    )

    try:
        with open("./queries/insert_users.sql", "w", encoding="utf-8") as f:
            fake = Faker()
            with conn.cursor() as cursor:
                insert_query = "INSERT INTO users (username, password) VALUES "
                values = []
                for i in range(10000):
                    username = fake.name()
                    username = "".join(c for c in username if c not in SPECIAL_CHARS).lower().replace(" ", "")
                    while username in usernames.keys():
                        username = fake.name()
                        username = "".join(c for c in username if c not in SPECIAL_CHARS).lower().replace(" ", "")
                    usernames[username] = 1
                    password = "Qqqqqq12"

                    values.append(f"('{username}', '{password}')")
                    if len(values) == 1000:
                        f.write(insert_query + ", ".join(values) + ";\n")
                        values = []

    except Exception as error:
        print(error)
    finally:
        if conn:
            cursor.close()
            conn.close()
