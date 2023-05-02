import { Pilot } from "./pilot.model";

export interface Car {
    id: string;
    brand: string;
    motorization: string;
    gearBox: string;
    cylindricalCapacity: number;
    horsePower: number;
    description: string;
    pilotID: string;
    username: string;
}

export class CarOne {
    constructor(
        public id: string,
        public brand: string,
        public motorization: string,
        public gearBox: string,
        public cylindricalCapacity: number,
        public horsePower: number,
        public description: string,
        public pilot: Pilot,
        public username: string
    ) {}
}

export class CarAddUpdate {
    constructor(  
        public brand: string,
        public motorization: string,
        public gearBox: string,
        public cylindricalCapacity: number,
        public horsePower: number,
        public description: string,){}
}