import random
import psycopg2
from constants import HOST, PORT, DATABASE, USER, PASSWORD, SPECIAL_CHARS

positions = {}


def insert_data_participation():
    conn = psycopg2.connect(
        host=HOST,
        port=PORT,
        database=DATABASE,
        user=USER,
        password=PASSWORD
    )

    try:
        with open("./queries/insert_participations.sql", "w", encoding="utf-8") as f:
            with conn.cursor() as cursor:
                cursor.execute("SELECT id from pilots")
                pilot_ids = [el[0] for el in cursor.fetchall()]
                cursor.execute("SELECT id from races")
                race_ids = [el[0] for el in cursor.fetchall()]
                pairs = set()
                insert_query = "INSERT INTO races_pilots (race_id, pilot_id, start_position, need_accommodation) VALUES "
                values = []
                for i in range(10000000):
                    pilot_id = random.choice(pilot_ids)
                    race_id = random.choice(race_ids)
                    while (pilot_id, race_id) in pairs:
                        pilot_id = random.choice(pilot_ids)
                        race_id = random.choice(race_ids)
                    pairs.add((pilot_id, race_id))

                    need_accommodation_list = [False, True]
                    need_accommodation = random.choice(need_accommodation_list)

                    if race_id not in positions.keys():
                        positions[race_id] = 1
                        start_position = 1
                    else:
                        start_position = positions[race_id] + 1
                        positions[race_id] = positions[race_id] + 1

                    values.append(f"({race_id}, {pilot_id}, '{start_position}', '{need_accommodation}')")

                    if len(values) == 1000:
                        f.write(insert_query + ", ".join(values) + ";\n")
                        values = []
    except Exception as error:
        print(error)
    finally:
        if conn:
            cursor.close()
            conn.close()
