import random
import psycopg2
from constants import HOST, PORT, DATABASE, USER, PASSWORD, SPECIAL_CHARS_DESCRIPTION
from faker import Faker


def insert_data_cars():
    conn = psycopg2.connect(
        host=HOST,
        port=PORT,
        database=DATABASE,
        user=USER,
        password=PASSWORD
    )

    try:
        with open("./queries/insert_cars.sql", "w", encoding="utf-8") as f:
            fake = Faker()
            with conn.cursor() as cursor:
                cursor.execute("SELECT id from Pilots")
                pilot_ids = [el[0] for el in cursor.fetchall()]
                insert_query = \
                    "INSERT INTO Cars (brand, motorization, gear_box, cylindrical_capacity, horse_power, description, pilot_id) VALUES "
                values = []
                for i in range(1000000):
                    brand_list = ["Audi", "BMW", "Mercedes", "Ferrari", "Lamborghini", "Aston Martin", "Volkswagen",
                                  "Renault", "Opel", "Seat", "Honda", "Hyundai", "KIA", "Suzuki", "Bugatti", "Skoda"]
                    brand = random.choice(brand_list)
                    motorization_list = ["diesel", "benzine"]
                    motorization = random.choice(motorization_list)
                    gear_box_list = ["automatic", "manual"]
                    gear_box = random.choice(gear_box_list)
                    cylindrical_capacity = random.randint(2000, 8000)
                    horse_power = random.randint(150, 800)
                    description = fake.paragraph(nb_sentences=5)
                    while len(description.split()) < 100:
                        description += " " + fake.paragraph(nb_sentences=5)
                    description = "".join(c for c in description if c not in SPECIAL_CHARS_DESCRIPTION)
                    pilot_id = random.choice(pilot_ids)
                    values.append(
                        f"('{brand}', '{motorization}', '{gear_box}', {cylindrical_capacity}, {horse_power},'{description}', {pilot_id})")
                    if len(values) == 1000:
                        f.write(insert_query + ", ".join(values) + ";\n")
                        values = []
    except Exception as error:
        print(error)
    finally:
        if conn:
            cursor.close()
            conn.close()
