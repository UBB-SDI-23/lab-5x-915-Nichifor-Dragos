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

export class PilotOne {
    constructor(
        public id: string,
        public firstName: string,
        public lastName: string,
        public nationality: string,
        public date: Date,
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