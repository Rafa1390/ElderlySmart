import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { take, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { RecreationalActivityService } from 'app/entities/recreational-activity/recreational-activity.service';
import { IRecreationalActivity, RecreationalActivity } from 'app/shared/model/recreational-activity.model';

describe('Service Tests', () => {
  describe('RecreationalActivity Service', () => {
    let injector: TestBed;
    let service: RecreationalActivityService;
    let httpMock: HttpTestingController;
    let elemDefault: IRecreationalActivity;
    let expectedResult;
    let currentDate: moment.Moment;
    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = {};
      injector = getTestBed();
      service = injector.get(RecreationalActivityService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new RecreationalActivity(0, 0, 'AAAAAAA', 'AAAAAAA', currentDate, 'AAAAAAA', 'AAAAAAA', 'AAAAAAA');
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            date: currentDate.format(DATE_FORMAT)
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

      it('should create a RecreationalActivity', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            date: currentDate.format(DATE_FORMAT)
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            date: currentDate
          },
          returnedFromService
        );
        service
          .create(new RecreationalActivity(null))
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));
        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: expected });
      });

      it('should update a RecreationalActivity', () => {
        const returnedFromService = Object.assign(
          {
            idRecreationalActivity: 1,
            name: 'BBBBBB',
            description: 'BBBBBB',
            date: currentDate.format(DATE_FORMAT),
            startTime: 'BBBBBB',
            endTime: 'BBBBBB',
            state: 'BBBBBB'
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            date: currentDate
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

      it('should return a list of RecreationalActivity', () => {
        const returnedFromService = Object.assign(
          {
            idRecreationalActivity: 1,
            name: 'BBBBBB',
            description: 'BBBBBB',
            date: currentDate.format(DATE_FORMAT),
            startTime: 'BBBBBB',
            endTime: 'BBBBBB',
            state: 'BBBBBB'
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            date: currentDate
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

      it('should delete a RecreationalActivity', () => {
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
