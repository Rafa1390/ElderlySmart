import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { take, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { MedicamentService } from 'app/entities/medicament/medicament.service';
import { IMedicament, Medicament } from 'app/shared/model/medicament.model';

describe('Service Tests', () => {
  describe('Medicament Service', () => {
    let injector: TestBed;
    let service: MedicamentService;
    let httpMock: HttpTestingController;
    let elemDefault: IMedicament;
    let expectedResult;
    let currentDate: moment.Moment;
    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = {};
      injector = getTestBed();
      service = injector.get(MedicamentService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new Medicament(0, 0, 'AAAAAAA', currentDate, 'AAAAAAA');
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            dateExpiry: currentDate.format(DATE_FORMAT)
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

      it('should create a Medicament', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            dateExpiry: currentDate.format(DATE_FORMAT)
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            dateExpiry: currentDate
          },
          returnedFromService
        );
        service
          .create(new Medicament(null))
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));
        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: expected });
      });

      it('should update a Medicament', () => {
        const returnedFromService = Object.assign(
          {
            idMedicament: 1,
            presentation: 'BBBBBB',
            dateExpiry: currentDate.format(DATE_FORMAT),
            cuantity: 'BBBBBB'
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            dateExpiry: currentDate
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

      it('should return a list of Medicament', () => {
        const returnedFromService = Object.assign(
          {
            idMedicament: 1,
            presentation: 'BBBBBB',
            dateExpiry: currentDate.format(DATE_FORMAT),
            cuantity: 'BBBBBB'
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            dateExpiry: currentDate
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

      it('should delete a Medicament', () => {
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
