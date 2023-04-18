import { Car } from "./car.model";
import { RacePilotDTO } from "./race.model";

export interface Pilot {
    id: string;
    firstName: string;
    lastName: string;
    nationality: string;
    date: Date;
    drivingExperience: number;
}

export interface PilotAll {
    id: string;
    firstName: string;
    lastName: string;
    nationality: string;
    date: Date;
    drivingExperience: number;
    numberOfRaces: number;
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
        public races: RacePilotDTO[]
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
        public needAccommodation: boolean
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