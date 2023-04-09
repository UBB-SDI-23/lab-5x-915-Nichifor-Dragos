import random
import psycopg2
from faker import Faker
from constants import HOST, PORT, DATABASE, USER, PASSWORD, SPECIAL_CHARS
from datetime import datetime, timedelta


def random_date(start, end):
    delta = end - start
    int_delta = (delta.days * 24 * 60 * 60) + delta.seconds
    random_second = random.randrange(int_delta)
    return start + timedelta(seconds=random_second)


def insert_data_races():
    conn = psycopg2.connect(
        host=HOST,
        port=PORT,
        database=DATABASE,
        user=USER,
        password=PASSWORD
    )

    try:
        with open("./queries/insert_races.sql", "w", encoding="utf-8") as f:
            fake = Faker()
            with conn.cursor() as cursor:
                insert_query = "INSERT INTO races (name, country, number_of_laps, lap_length, date) VALUES "
                values = []
                for i in range(1000000):
                    name = fake.name()
                    name = "".join(c for c in name if c not in SPECIAL_CHARS).lower()
                    country = fake.country()
                    country = "".join(c for c in country if c not in SPECIAL_CHARS).lower()
                    number_of_laps = random.randint(3, 20)
                    lap_length = random.randint(10, 80)

                    d1 = datetime.strptime('6/1/2023 1:30 PM', '%m/%d/%Y %I:%M %p')
                    d2 = datetime.strptime('12/29/2023 4:50 AM', '%m/%d/%Y %I:%M %p')

                    date = random_date(d1, d2)

                    values.append(f"('{name}', '{country}', {number_of_laps}, {lap_length}, '{date}')")
                    if len(values) == 1000:
                        f.write(insert_query + ", ".join(values) + ";\n")
                        values = []
    except Exception as error:
        print(error)
    finally:
        if conn:
            cursor.close()
            conn.close()
