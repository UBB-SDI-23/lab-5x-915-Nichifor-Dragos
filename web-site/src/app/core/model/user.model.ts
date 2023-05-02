export interface UserProfile {
    id: string,
    bio: string,
    location: string,
    birthdate: Date,
    gender: string,
    maritalStatus: string
}

export interface User {
    id: string,
    username: string,
    roles: string[]
}

export class UserProfileUpdate {
    constructor(
        public id: string,
        public bio: string,
        public location: string,
        public birthdate: Date,
        public gender: string,
        public maritalStatus: string
    ) {}
}