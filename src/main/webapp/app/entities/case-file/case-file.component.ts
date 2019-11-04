import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { filter, map } from 'rxjs/operators';
import { JhiEventManager } from 'ng-jhipster';

import { ICaseFile } from 'app/shared/model/case-file.model';
import { AccountService } from 'app/core/auth/account.service';
import { CaseFileService } from './case-file.service';

@Component({
  selector: 'jhi-case-file',
  templateUrl: './case-file.component.html'
})
export class CaseFileComponent implements OnInit, OnDestroy {
  caseFiles: ICaseFile[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected caseFileService: CaseFileService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.caseFileService
      .query()
      .pipe(
        filter((res: HttpResponse<ICaseFile[]>) => res.ok),
        map((res: HttpResponse<ICaseFile[]>) => res.body)
      )
      .subscribe((res: ICaseFile[]) => {
        this.caseFiles = res;
      });
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().subscribe(account => {
      this.currentAccount = account;
    });
    this.registerChangeInCaseFiles();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: ICaseFile) {
    return item.id;
  }

  registerChangeInCaseFiles() {
    this.eventSubscriber = this.eventManager.subscribe('caseFileListModification', response => this.loadAll());
  }
}
