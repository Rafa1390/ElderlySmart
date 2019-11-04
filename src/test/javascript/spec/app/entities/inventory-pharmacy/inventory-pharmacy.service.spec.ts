import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { take, map } from 'rxjs/operators';
import { InventoryPharmacyService } from 'app/entities/inventory-pharmacy/inventory-pharmacy.service';
import { IInventoryPharmacy, InventoryPharmacy } from 'app/shared/model/inventory-pharmacy.model';

describe('Service Tests', () => {
  describe('InventoryPharmacy Service', () => {
    let injector: TestBed;
    let service: InventoryPharmacyService;
    let httpMock: HttpTestingController;
    let elemDefault: IInventoryPharmacy;
    let expectedResult;
    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = {};
      injector = getTestBed();
      service = injector.get(InventoryPharmacyService);
      httpMock = injector.get(HttpTestingController);

      elemDefault = new InventoryPharmacy(0, 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', 0, 0, 0);
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

      it('should create a InventoryPharmacy', () => {
        const returnedFromService = Object.assign(
          {
            id: 0
          },
          elemDefault
        );
        const expected = Object.assign({}, returnedFromService);
        service
          .create(new InventoryPharmacy(null))
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));
        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: expected });
      });

      it('should update a InventoryPharmacy', () => {
        const returnedFromService = Object.assign(
          {
            idInventoryPharmacy: 'BBBBBB',
            code: 'BBBBBB',
            name: 'BBBBBB',
            purchasePrice: 1,
            salePrice: 1,
            cuantity: 1
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

      it('should return a list of InventoryPharmacy', () => {
        const returnedFromService = Object.assign(
          {
            idInventoryPharmacy: 'BBBBBB',
            code: 'BBBBBB',
            name: 'BBBBBB',
            purchasePrice: 1,
            salePrice: 1,
            cuantity: 1
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

      it('should delete a InventoryPharmacy', () => {
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
