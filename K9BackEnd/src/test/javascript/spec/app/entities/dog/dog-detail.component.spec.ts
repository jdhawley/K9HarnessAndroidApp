/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { K9BackEndTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { DogDetailComponent } from '../../../../../../main/webapp/app/entities/dog/dog-detail.component';
import { DogService } from '../../../../../../main/webapp/app/entities/dog/dog.service';
import { Dog } from '../../../../../../main/webapp/app/entities/dog/dog.model';

describe('Component Tests', () => {

    describe('Dog Management Detail Component', () => {
        let comp: DogDetailComponent;
        let fixture: ComponentFixture<DogDetailComponent>;
        let service: DogService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [K9BackEndTestModule],
                declarations: [DogDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    DogService,
                    JhiEventManager
                ]
            }).overrideTemplate(DogDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(DogDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(DogService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new Dog(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.dog).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
