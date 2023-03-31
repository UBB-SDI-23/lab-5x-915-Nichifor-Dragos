import { PilotRaceDTO } from "./pilot.model";

export interface Race {
    id: string;
    name: string;
    country: string
    numberOfLaps: number;
    lapLength: number;
    date: Date
}

export class RaceAddUpdate {
    constructor(
        public name: string,
        public country: string,
        public numberOfLaps: number,
        public lapLength: number,
        public date: Date
    ) {}
}

export class RaceOne {
    constructor(
        public id: string,
        public name: string,
        public country: string,
        public numberOfLaps: number,
        public lapLength: number,
        public date: Date,
        public pilots: PilotRaceDTO[]
    ) {}
}