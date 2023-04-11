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
                    name_list = ["Monza Circuit", "Silverstone Circuit", "Circuit de Monaco",
                                 "Spa-Francorchamps Circuit", "Suzuka Circuit",
                                 "Circuit de la Sarthe (24 Hours of Le Mans)", "Circuit of the Americas",
                                 "Bathurst Mount Panorama Circuit", "Nurburgring Nordschleife",
                                 "Indianapolis Motor Speedway", "Hockenheimring Circuit", "Red Bull Ring Circuit",
                                 "Circuit Gilles Villeneuve", "Autodromo Nazionale di Monza", "Circuit Paul Ricard",
                                 "Mugello Circuit", "Autodromo Jose Carlos Pace", "Hungaroring Circuit",
                                 "Sepang International Circuit", "Shanghai International Circuit", "Yas Marina Circuit",
                                 "Circuit Zandvoort", "Imola Circuit", "Circuit Ricardo Tormo",
                                 "Circuit de Barcelona-Catalunya", "Losail International Circuit",
                                 "Phillip Island Grand Prix Circuit", "Valencia Street Circuit",
                                 "Circuit de Nevers Magny-Cours", "Autodromo Internacional do Algarve", "Jerez Circuit",
                                 "Misano World Circuit Marco Simoncelli", "Brands Hatch Circuit",
                                 "Donington Park Circuit", "Oulton Park Circuit", "Thruxton Circuit",
                                 "Rockingham Speedway", "Snetterton Circuit", "Cadwell Park Circuit",
                                 "Anglesey Circuit", "Castle Combe Circuit", "Silverstone Circuit (National)",
                                 "Silverstone Circuit (International)", "Silverstone Circuit (Grand Prix)"]
                    name = random.choice(name_list)
                    name = "".join(c for c in name if c not in SPECIAL_CHARS_DESCRIPTION)

                    country = fake.country()
                    country = "".join(c for c in country if c not in SPECIAL_CHARS_DESCRIPTION)

                    number_of_laps = random.randint(5, 15)
                    lap_length = random.randint(30, 80)

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
