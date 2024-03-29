import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { take, map } from 'rxjs/operators';
import { UserAppService } from 'app/entities/user-app/user-app.service';
import { IUserApp, UserApp } from 'app/shared/model/user-app.model';

describe('Service Tests', () => {
  describe('UserApp Service', () => {
    let injector: TestBed;
    let service: UserAppService;
    let httpMock: HttpTestingController;
    let elemDefault: IUserApp;
    let expectedResult;
    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = {};
      injector = getTestBed();
      service = injector.get(UserAppService);
      httpMock = injector.get(HttpTestingController);

      elemDefault = new UserApp(
        0,
        'AAAAAAA',
        0,
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        0,
        'AAAAAAA',
        'AAAAAAA'
      );
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign({}, elemDefault);
        service
          .find(123)
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: elemDefault });
      });

      it('should create a UserApp', () => {
        const returnedFromService = Object.assign(
          {
            id: 0
          },
          elemDefault
        );
        const expected = Object.assign({}, returnedFromService);
        service
          .create(new UserApp(null))
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));
        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: expected });
      });

      it('should update a UserApp', () => {
        const returnedFromService = Object.assign(
          {
            identification: 'BBBBBB',
            idTypeUser: 1,
            email: 'BBBBBB',
            name: 'BBBBBB',
            name2: 'BBBBBB',
            lastName: 'BBBBBB',
            lastName2: 'BBBBBB',
            phone1: 'BBBBBB',
            phone2: 'BBBBBB',
            age: 1,
            password: 'BBBBBB',
            state: 'BBBBBB'
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);
        service
          .update(expected)
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));
        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: expected });
      });

      it('should return a list of UserApp', () => {
        const returnedFromService = Object.assign(
          {
            identification: 'BBBBBB',
            idTypeUser: 1,
            email: 'BBBBBB',
            name: 'BBBBBB',
            name2: 'BBBBBB',
            lastName: 'BBBBBB',
            lastName2: 'BBBBBB',
            phone1: 'BBBBBB',
            phone2: 'BBBBBB',
            age: 1,
            password: 'BBBBBB',
            state: 'BBBBBB'
          },
          elemDefault
        );
        const expected = Object.assign({}, returnedFromService);
        service
          .query(expected)
          .pipe(
            take(1),
            map(resp => resp.body)
          )
          .subscribe(body => (expectedResult = body));
        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a UserApp', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
