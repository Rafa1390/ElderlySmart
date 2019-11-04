import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { take, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { ElderlyService } from 'app/entities/elderly/elderly.service';
import { IElderly, Elderly } from 'app/shared/model/elderly.model';

describe('Service Tests', () => {
  describe('Elderly Service', () => {
    let injector: TestBed;
    let service: ElderlyService;
    let httpMock: HttpTestingController;
    let elemDefault: IElderly;
    let expectedResult;
    let currentDate: moment.Moment;
    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = {};
      injector = getTestBed();
      service = injector.get(ElderlyService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new Elderly(0, 0, 'AAAAAAA', 'AAAAAAA', currentDate, 'AAAAAAA');
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            admissionDate: currentDate.format(DATE_FORMAT)
          },
          elemDefault
        );
        service
          .find(123)
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: elemDefault });
      });

      it('should create a Elderly', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            admissionDate: currentDate.format(DATE_FORMAT)
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            admissionDate: currentDate
          },
          returnedFromService
        );
        service
          .create(new Elderly(null))
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));
        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: expected });
      });

      it('should update a Elderly', () => {
        const returnedFromService = Object.assign(
          {
            idElderly: 1,
            nationality: 'BBBBBB',
            address: 'BBBBBB',
            admissionDate: currentDate.format(DATE_FORMAT),
            state: 'BBBBBB'
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            admissionDate: currentDate
          },
          returnedFromService
        );
        service
          .update(expected)
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));
        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: expected });
      });

      it('should return a list of Elderly', () => {
        const returnedFromService = Object.assign(
          {
            idElderly: 1,
            nationality: 'BBBBBB',
            address: 'BBBBBB',
            admissionDate: currentDate.format(DATE_FORMAT),
            state: 'BBBBBB'
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            admissionDate: currentDate
          },
          returnedFromService
        );
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

      it('should delete a Elderly', () => {
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
