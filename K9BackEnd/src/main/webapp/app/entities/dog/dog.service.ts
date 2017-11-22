import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { SERVER_API_URL } from '../../app.constants';

import { Dog } from './dog.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class DogService {

    private resourceUrl = SERVER_API_URL + 'api/dogs';

    constructor(private http: Http) { }

    create(dog: Dog): Observable<Dog> {
        const copy = this.convert(dog);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    update(dog: Dog): Observable<Dog> {
        const copy = this.convert(dog);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    find(id: number): Observable<Dog> {
        return this.http.get(`${this.resourceUrl}/${id}`).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    query(req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(this.resourceUrl, options)
            .map((res: Response) => this.convertResponse(res));
    }

    delete(id: number): Observable<Response> {
        return this.http.delete(`${this.resourceUrl}/${id}`);
    }

    private convertResponse(res: Response): ResponseWrapper {
        const jsonResponse = res.json();
        const result = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            result.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return new ResponseWrapper(res.headers, result, res.status);
    }

    /**
     * Convert a returned JSON object to Dog.
     */
    private convertItemFromServer(json: any): Dog {
        const entity: Dog = Object.assign(new Dog(), json);
        return entity;
    }

    /**
     * Convert a Dog to a JSON which can be sent to the server.
     */
    private convert(dog: Dog): Dog {
        const copy: Dog = Object.assign({}, dog);
        return copy;
    }
}
