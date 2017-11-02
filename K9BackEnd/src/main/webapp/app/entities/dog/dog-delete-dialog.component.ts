import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Dog } from './dog.model';
import { DogPopupService } from './dog-popup.service';
import { DogService } from './dog.service';

@Component({
    selector: 'jhi-dog-delete-dialog',
    templateUrl: './dog-delete-dialog.component.html'
})
export class DogDeleteDialogComponent {

    dog: Dog;

    constructor(
        private dogService: DogService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.dogService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'dogListModification',
                content: 'Deleted an dog'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-dog-delete-popup',
    template: ''
})
export class DogDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private dogPopupService: DogPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.dogPopupService
                .open(DogDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
