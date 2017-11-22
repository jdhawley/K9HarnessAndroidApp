import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { DogComponent } from './dog.component';
import { DogDetailComponent } from './dog-detail.component';
import { DogPopupComponent } from './dog-dialog.component';
import { DogDeletePopupComponent } from './dog-delete-dialog.component';

export const dogRoute: Routes = [
    {
        path: 'dog',
        component: DogComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Dogs'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'dog/:id',
        component: DogDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Dogs'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const dogPopupRoute: Routes = [
    {
        path: 'dog-new',
        component: DogPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Dogs'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'dog/:id/edit',
        component: DogPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Dogs'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'dog/:id/delete',
        component: DogDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Dogs'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
