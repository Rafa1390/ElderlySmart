import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { filter, map } from 'rxjs/operators';
import { JhiEventManager } from 'ng-jhipster';

import { IEmployee } from 'app/shared/model/employee.model';
import { AccountService } from 'app/core/auth/account.service';
import { EmployeeService } from './employee.service';

@Component({
  selector: 'jhi-employee',
  templateUrl: './employee.component.html'
})
export class EmployeeComponent implements OnInit, OnDestroy {
  employees: IEmployee[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected employeeService: EmployeeService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.employeeService
      .query()
      .pipe(
        filter((res: HttpResponse<IEmployee[]>) => res.ok),
        map((res: HttpResponse<IEmployee[]>) => res.body)
      )
      .subscribe((res: IEmployee[]) => {
        this.employees = res;
      });
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().subscribe(account => {
      this.currentAccount = account;
    });
    this.registerChangeInEmployees();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IEmployee) {
    return item.id;
  }

  registerChangeInEmployees() {
    this.eventSubscriber = this.eventManager.subscribe('employeeListModification', response => this.loadAll());
  }
}
