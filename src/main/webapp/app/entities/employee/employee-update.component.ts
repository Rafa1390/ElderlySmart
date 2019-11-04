import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IEmployee, Employee } from 'app/shared/model/employee.model';
import { EmployeeService } from './employee.service';
import { IAsylum } from 'app/shared/model/asylum.model';
import { AsylumService } from 'app/entities/asylum/asylum.service';
import { IUserApp } from 'app/shared/model/user-app.model';
import { UserAppService } from 'app/entities/user-app/user-app.service';

@Component({
  selector: 'jhi-employee-update',
  templateUrl: './employee-update.component.html'
})
export class EmployeeUpdateComponent implements OnInit {
  isSaving: boolean;

  asylums: IAsylum[];

  userapps: IUserApp[];

  editForm = this.fb.group({
    id: [],
    idEmployee: [],
    address: [],
    asylum: [],
    userApp: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected employeeService: EmployeeService,
    protected asylumService: AsylumService,
    protected userAppService: UserAppService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ employee }) => {
      this.updateForm(employee);
    });
    this.asylumService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IAsylum[]>) => mayBeOk.ok),
        map((response: HttpResponse<IAsylum[]>) => response.body)
      )
      .subscribe((res: IAsylum[]) => (this.asylums = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.userAppService
      .query({ filter: 'employee-is-null' })
      .pipe(
        filter((mayBeOk: HttpResponse<IUserApp[]>) => mayBeOk.ok),
        map((response: HttpResponse<IUserApp[]>) => response.body)
      )
      .subscribe(
        (res: IUserApp[]) => {
          if (!this.editForm.get('userApp').value || !this.editForm.get('userApp').value.id) {
            this.userapps = res;
          } else {
            this.userAppService
              .find(this.editForm.get('userApp').value.id)
              .pipe(
                filter((subResMayBeOk: HttpResponse<IUserApp>) => subResMayBeOk.ok),
                map((subResponse: HttpResponse<IUserApp>) => subResponse.body)
              )
              .subscribe(
                (subRes: IUserApp) => (this.userapps = [subRes].concat(res)),
                (subRes: HttpErrorResponse) => this.onError(subRes.message)
              );
          }
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  updateForm(employee: IEmployee) {
    this.editForm.patchValue({
      id: employee.id,
      idEmployee: employee.idEmployee,
      address: employee.address,
      asylum: employee.asylum,
      userApp: employee.userApp
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const employee = this.createFromForm();
    if (employee.id !== undefined) {
      this.subscribeToSaveResponse(this.employeeService.update(employee));
    } else {
      this.subscribeToSaveResponse(this.employeeService.create(employee));
    }
  }

  private createFromForm(): IEmployee {
    return {
      ...new Employee(),
      id: this.editForm.get(['id']).value,
      idEmployee: this.editForm.get(['idEmployee']).value,
      address: this.editForm.get(['address']).value,
      asylum: this.editForm.get(['asylum']).value,
      userApp: this.editForm.get(['userApp']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IEmployee>>) {
    result.subscribe(() => this.onSaveSuccess(), () => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }

  trackAsylumById(index: number, item: IAsylum) {
    return item.id;
  }

  trackUserAppById(index: number, item: IUserApp) {
    return item.id;
  }
}
