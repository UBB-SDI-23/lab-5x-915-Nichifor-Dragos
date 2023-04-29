import psycopg2
from constants import HOST, PORT, DATABASE, USER, PASSWORD


def insert_data_user_roles():
    conn = psycopg2.connect(
        host=HOST,
        port=PORT,
        database=DATABASE,
        user=USER,
        password=PASSWORD
    )

    try:
        with open("./queries/insert_user_roles.sql", "w", encoding="utf-8") as f:
            with conn.cursor() as cursor:
                cursor.execute("SELECT id from users")
                user_ids = [el[0] for el in cursor.fetchall()]
                cursor.execute("SELECT id from roles WHERE name='ROLE_USER'")
                role_id = [el[0] for el in cursor.fetchall()]
                insert_query = \
                    "INSERT INTO user_roles (user_id, role_id) VALUES "
                values = []

                for user_id in user_ids:
                    values.append(f"({user_id}, {role_id[0]})")
                    if len(values) == 1000:
                        f.write(insert_query + ", ".join(values) + ";\n")
                        values = []

    except Exception as error:
        print(error)
    finally:
        if conn:
            cursor.close()
            conn.close()
