import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { take, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { CaseFileService } from 'app/entities/case-file/case-file.service';
import { ICaseFile, CaseFile } from 'app/shared/model/case-file.model';

describe('Service Tests', () => {
  describe('CaseFile Service', () => {
    let injector: TestBed;
    let service: CaseFileService;
    let httpMock: HttpTestingController;
    let elemDefault: ICaseFile;
    let expectedResult;
    let currentDate: moment.Moment;
    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = {};
      injector = getTestBed();
      service = injector.get(CaseFileService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new CaseFile(
        0,
        0,
        currentDate,
        'AAAAAAA',
        0,
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        currentDate,
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA'
      );
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            creationDate: currentDate.format(DATE_FORMAT),
            birth: currentDate.format(DATE_FORMAT)
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

      it('should create a CaseFile', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            creationDate: currentDate.format(DATE_FORMAT),
            birth: currentDate.format(DATE_FORMAT)
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            creationDate: currentDate,
            birth: currentDate
          },
          returnedFromService
        );
        service
          .create(new CaseFile(null))
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));
        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: expected });
      });

      it('should update a CaseFile', () => {
        const returnedFromService = Object.assign(
          {
            idCaseFile: 1,
            creationDate: currentDate.format(DATE_FORMAT),
            fullNameElderly: 'BBBBBB',
            age: 1,
            religion: 'BBBBBB',
            nationality: 'BBBBBB',
            weight: 'BBBBBB',
            height: 'BBBBBB',
            birth: currentDate.format(DATE_FORMAT),
            gender: 'BBBBBB',
            bloodType: 'BBBBBB',
            resuscitation: 'BBBBBB',
            organDonation: 'BBBBBB',
            state: 'BBBBBB'
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            creationDate: currentDate,
            birth: currentDate
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

      it('should return a list of CaseFile', () => {
        const returnedFromService = Object.assign(
          {
            idCaseFile: 1,
            creationDate: currentDate.format(DATE_FORMAT),
            fullNameElderly: 'BBBBBB',
            age: 1,
            religion: 'BBBBBB',
            nationality: 'BBBBBB',
            weight: 'BBBBBB',
            height: 'BBBBBB',
            birth: currentDate.format(DATE_FORMAT),
            gender: 'BBBBBB',
            bloodType: 'BBBBBB',
            resuscitation: 'BBBBBB',
            organDonation: 'BBBBBB',
            state: 'BBBBBB'
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            creationDate: currentDate,
            birth: currentDate
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

      it('should delete a CaseFile', () => {
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
