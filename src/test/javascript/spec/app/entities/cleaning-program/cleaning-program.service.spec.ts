import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { take, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { CleaningProgramService } from 'app/entities/cleaning-program/cleaning-program.service';
import { ICleaningProgram, CleaningProgram } from 'app/shared/model/cleaning-program.model';

describe('Service Tests', () => {
  describe('CleaningProgram Service', () => {
    let injector: TestBed;
    let service: CleaningProgramService;
    let httpMock: HttpTestingController;
    let elemDefault: ICleaningProgram;
    let expectedResult;
    let currentDate: moment.Moment;
    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = {};
      injector = getTestBed();
      service = injector.get(CleaningProgramService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new CleaningProgram(0, 0, currentDate, 'AAAAAAA', 'AAAAAAA');
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            day: currentDate.format(DATE_FORMAT)
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

      it('should create a CleaningProgram', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            day: currentDate.format(DATE_FORMAT)
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            day: currentDate
          },
          returnedFromService
        );
        service
          .create(new CleaningProgram(null))
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));
        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: expected });
      });

      it('should update a CleaningProgram', () => {
        const returnedFromService = Object.assign(
          {
            idCleaningProgram: 1,
            day: currentDate.format(DATE_FORMAT),
            time: 'BBBBBB',
            cleaningTask: 'BBBBBB'
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            day: currentDate
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

      it('should return a list of CleaningProgram', () => {
        const returnedFromService = Object.assign(
          {
            idCleaningProgram: 1,
            day: currentDate.format(DATE_FORMAT),
            time: 'BBBBBB',
            cleaningTask: 'BBBBBB'
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            day: currentDate
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

      it('should delete a CleaningProgram', () => {
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
