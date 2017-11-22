import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { K9BackEndDogModule } from './dog/dog.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    imports: [
        K9BackEndDogModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class K9BackEndEntityModule {}
