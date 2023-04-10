from insert.insert_cars import insert_data_cars
from insert.insert_participations import insert_data_participations
from insert.insert_pilots import insert_data_pilots
from insert.insert_races import insert_data_races


def insert_races():
    print("Inserting races...")
    insert_data_races()


def insert_cars():
    print("Inserting cars...")
    insert_data_cars()


def insert_pilots():
    print("Inserting pilots...")
    insert_data_pilots()


def insert_participations():
    print("Inserting participations...")
    insert_data_participations()


def insert():
    print("===== Insert script is running! =====")
    #insert_races()
    #insert_pilots()
    insert_cars()
    #insert_participations()


if __name__ == '__main__':
    insert()
