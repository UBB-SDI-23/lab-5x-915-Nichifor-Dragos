export interface Race {
    id: number;
    name: string;
    country: string
    numberOfLaps: number;
    lapLength: number;
    date: Date
}

export class RaceAdd {
    constructor(
        public name: string,
        public country: string,
        public numberOfLaps: number,
        public lapLength: number,
        public date: Date
    ) {}
}