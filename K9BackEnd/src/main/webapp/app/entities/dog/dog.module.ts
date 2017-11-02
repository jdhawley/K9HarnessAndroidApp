import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { K9BackEndSharedModule } from '../../shared';
import { K9BackEndAdminModule } from '../../admin/admin.module';
import {
    DogService,
    DogPopupService,
    DogComponent,
    DogDetailComponent,
    DogDialogComponent,
    DogPopupComponent,
    DogDeletePopupComponent,
    DogDeleteDialogComponent,
    dogRoute,
    dogPopupRoute,
} from './';

const ENTITY_STATES = [
    ...dogRoute,
    ...dogPopupRoute,
];

@NgModule({
    imports: [
        K9BackEndSharedModule,
        K9BackEndAdminModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        DogComponent,
        DogDetailComponent,
        DogDialogComponent,
        DogDeleteDialogComponent,
        DogPopupComponent,
        DogDeletePopupComponent,
    ],
    entryComponents: [
        DogComponent,
        DogDialogComponent,
        DogPopupComponent,
        DogDeleteDialogComponent,
        DogDeletePopupComponent,
    ],
    providers: [
        DogService,
        DogPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class K9BackEndDogModule {}
