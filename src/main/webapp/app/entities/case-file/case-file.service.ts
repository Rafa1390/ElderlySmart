import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ICaseFile } from 'app/shared/model/case-file.model';

type EntityResponseType = HttpResponse<ICaseFile>;
type EntityArrayResponseType = HttpResponse<ICaseFile[]>;

@Injectable({ providedIn: 'root' })
export class CaseFileService {
  public resourceUrl = SERVER_API_URL + 'api/case-files';

  constructor(protected http: HttpClient) {}

  create(caseFile: ICaseFile): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(caseFile);
    return this.http
      .post<ICaseFile>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(caseFile: ICaseFile): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(caseFile);
    return this.http
      .put<ICaseFile>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ICaseFile>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ICaseFile[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(caseFile: ICaseFile): ICaseFile {
    const copy: ICaseFile = Object.assign({}, caseFile, {
      creationDate: caseFile.creationDate != null && caseFile.creationDate.isValid() ? caseFile.creationDate.format(DATE_FORMAT) : null,
      birth: caseFile.birth != null && caseFile.birth.isValid() ? caseFile.birth.format(DATE_FORMAT) : null
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.creationDate = res.body.creationDate != null ? moment(res.body.creationDate) : null;
      res.body.birth = res.body.birth != null ? moment(res.body.birth) : null;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((caseFile: ICaseFile) => {
        caseFile.creationDate = caseFile.creationDate != null ? moment(caseFile.creationDate) : null;
        caseFile.birth = caseFile.birth != null ? moment(caseFile.birth) : null;
      });
    }
    return res;
  }
}
