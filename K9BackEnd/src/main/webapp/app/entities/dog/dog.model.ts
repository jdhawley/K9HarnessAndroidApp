import { BaseEntity } from './../../shared';

export class Dog implements BaseEntity {
    constructor(
        public id?: number,
        public coretemp?: number,
        public respiratoryrate?: number,
        public abtemp?: number,
        public name?: string,
        public heartrate?: number,
        public maxcoretemp?: number,
        public maxrespiratoryrate?: number,
        public maxabtemp?: number,
        public maxheartrate?: number,
        public ownsId?: number,
    ) {
    }
}
