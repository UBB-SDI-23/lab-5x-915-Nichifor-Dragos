export interface Pilot {
    id: number;
    firstName: string;
    lastName: string;
    nationality: string;
    date: Date;
    drivingExperience: number;
}

export class PilotRaceDTO {
    constructor(
        public pilotId: number,
        public startPosition: number,
        public needAccommodation: boolean
    ){}
}