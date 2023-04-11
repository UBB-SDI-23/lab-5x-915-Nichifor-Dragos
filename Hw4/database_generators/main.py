from insert.insert_cars import insert_data_cars
from insert.insert_participations import insert_data_participation
from insert.insert_pilots import insert_data_pilots
from insert.insert_races import insert_data_races


def insert_races():
    print("Inserting races...")
    insert_data_races()
    print("Finished inserting races")


def insert_cars():
    print("Inserting cars...")
    insert_data_cars()
    print("Finished inserting cars")


def insert_pilots():
    print("Inserting pilots...")
    insert_data_pilots()
    print("Finished inserting pilots")


def insert_participation():
    print("Inserting participation...")
    insert_data_participation()
    print("Finished inserting participation")


def insert():
    print("===== Insert script is running! =====")
    # insert_races()
    # insert_pilots()
    # insert_cars()
    # insert_participation()
    print("===== Insert script was successful! =====")


if __name__ == '__main__':
    insert()
