import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { Dog } from './dog.model';
import { DogService } from './dog.service';

@Component({
    selector: 'jhi-dog-detail',
    templateUrl: './dog-detail.component.html'
})
export class DogDetailComponent implements OnInit, OnDestroy {

    dog: Dog;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private dogService: DogService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInDogs();
    }

    load(id) {
        this.dogService.find(id).subscribe((dog) => {
            this.dog = dog;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInDogs() {
        this.eventSubscriber = this.eventManager.subscribe(
            'dogListModification',
            (response) => this.load(this.dog.id)
        );
    }
}
