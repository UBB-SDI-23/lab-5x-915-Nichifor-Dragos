import { PilotRaceDTO } from "./pilot.model";

export interface Race {
    id: string;
    name: string;
    country: string
    numberOfLaps: number;
    lapLength: number;
    date: string
}

export class RaceAddUpdate {
    constructor(
        public name: string,
        public country: string,
        public numberOfLaps: number,
        public lapLength: number,
        public date: string
    ) {}
}

export class RaceOne {
    constructor(
        public id: string,
        public name: string,
        public country: string,
        public numberOfLaps: number,
        public lapLength: number,
        public date: string,
        public pilots: PilotRaceDTO[]
    ) {}
}

export class RacePilotDTO {
    constructor(
        public raceId: string,
        public startPosition: number,
        public needAccommodation: boolean
    ){}
}

export class RaceStatisticDTO {
    constructor (
        public raceID: string,
        public name: string,
        public numberOfPilots: number
    ) {}
}

export class ParticipationAdd {
    constructor(
        public startPosition: number,
        public needAccommodation: boolean
    ) {}
}