import { Car } from "./car.model";
import { RacePilotDTO } from "./race.model";
import { User } from "./user.model";

export interface Pilot {
    id: string;
    firstName: string;
    lastName: string;
    nationality: string;
    date: Date;
    drivingExperience: number;
    username: string;
}

export interface PilotAll {
    id: string;
    firstName: string;
    lastName: string;
    nationality: string;
    date: Date;
    drivingExperience: number;
    numberOfRaces: number;
    username: string;
}

export class PilotOne {
    constructor(
        public id: string,
        public firstName: string,
        public lastName: string,
        public nationality: string,
        public date: string,
        public drivingExperience: number,
        public cars: Car[],
        public races: RacePilotDTO[],
        public username: string
    ) {}
}

export class PilotAddUpdate {
    constructor(
        public firstName: string,
        public lastName: string,
        public nationality: string,
        public date: string,
        public drivingExperience: number,
    ) {}
}

export class PilotRaceDTO {
    constructor(
        public pilotId: string,
        public startPosition: number,
        public needAccommodation: boolean,
        public user: User
    ){}
}

export class PilotStatisticDTO {
    constructor (
        public pilotID: string,
        public firstName: string,
        public lastName: string,
        public numberOfCars: number
    ) {}
}