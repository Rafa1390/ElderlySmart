import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'user-app',
        loadChildren: () => import('./user-app/user-app.module').then(m => m.ElderlySmartUserAppModule)
      },
      {
        path: 'asylum',
        loadChildren: () => import('./asylum/asylum.module').then(m => m.ElderlySmartAsylumModule)
      },
      {
        path: 'cleaning-program',
        loadChildren: () => import('./cleaning-program/cleaning-program.module').then(m => m.ElderlySmartCleaningProgramModule)
      },
      {
        path: 'employee',
        loadChildren: () => import('./employee/employee.module').then(m => m.ElderlySmartEmployeeModule)
      },
      {
        path: 'food-menu',
        loadChildren: () => import('./food-menu/food-menu.module').then(m => m.ElderlySmartFoodMenuModule)
      },
      {
        path: 'medicament',
        loadChildren: () => import('./medicament/medicament.module').then(m => m.ElderlySmartMedicamentModule)
      },
      {
        path: 'product',
        loadChildren: () => import('./product/product.module').then(m => m.ElderlySmartProductModule)
      },
      {
        path: 'provider-history',
        loadChildren: () => import('./provider-history/provider-history.module').then(m => m.ElderlySmartProviderHistoryModule)
      },
      {
        path: 'allergies',
        loadChildren: () => import('./allergies/allergies.module').then(m => m.ElderlySmartAllergiesModule)
      },
      {
        path: 'case-file',
        loadChildren: () => import('./case-file/case-file.module').then(m => m.ElderlySmartCaseFileModule)
      },
      {
        path: 'elderly',
        loadChildren: () => import('./elderly/elderly.module').then(m => m.ElderlySmartElderlyModule)
      },
      {
        path: 'inventory-pharmacy',
        loadChildren: () => import('./inventory-pharmacy/inventory-pharmacy.module').then(m => m.ElderlySmartInventoryPharmacyModule)
      },
      {
        path: 'inventory-provider',
        loadChildren: () => import('./inventory-provider/inventory-provider.module').then(m => m.ElderlySmartInventoryProviderModule)
      },
      {
        path: 'pathologies',
        loadChildren: () => import('./pathologies/pathologies.module').then(m => m.ElderlySmartPathologiesModule)
      },
      {
        path: 'provider',
        loadChildren: () => import('./provider/provider.module').then(m => m.ElderlySmartProviderModule)
      },
      {
        path: 'recreational-activity',
        loadChildren: () =>
          import('./recreational-activity/recreational-activity.module').then(m => m.ElderlySmartRecreationalActivityModule)
      },
      {
        path: 'pharmacy',
        loadChildren: () => import('./pharmacy/pharmacy.module').then(m => m.ElderlySmartPharmacyModule)
      },
      {
        path: 'doctor',
        loadChildren: () => import('./doctor/doctor.module').then(m => m.ElderlySmartDoctorModule)
      },
      {
        path: 'partner',
        loadChildren: () => import('./partner/partner.module').then(m => m.ElderlySmartPartnerModule)
      },
      {
        path: 'family',
        loadChildren: () => import('./family/family.module').then(m => m.ElderlySmartFamilyModule)
      },
      {
        path: 'mortuary',
        loadChildren: () => import('./mortuary/mortuary.module').then(m => m.ElderlySmartMortuaryModule)
      },
      {
        path: 'funeral-history',
        loadChildren: () => import('./funeral-history/funeral-history.module').then(m => m.ElderlySmartFuneralHistoryModule)
      },
      {
        path: 'funeral-packages',
        loadChildren: () => import('./funeral-packages/funeral-packages.module').then(m => m.ElderlySmartFuneralPackagesModule)
      },
      {
        path: 'donation-history',
        loadChildren: () => import('./donation-history/donation-history.module').then(m => m.ElderlySmartDonationHistoryModule)
      },
      {
        path: 'pharmacy-history',
        loadChildren: () => import('./pharmacy-history/pharmacy-history.module').then(m => m.ElderlySmartPharmacyHistoryModule)
      },
      {
        path: 'prescription',
        loadChildren: () => import('./prescription/prescription.module').then(m => m.ElderlySmartPrescriptionModule)
      },
      {
        path: 'prescription-notification',
        loadChildren: () =>
          import('./prescription-notification/prescription-notification.module').then(m => m.ElderlySmartPrescriptionNotificationModule)
      },
      {
        path: 'medication-schedule',
        loadChildren: () => import('./medication-schedule/medication-schedule.module').then(m => m.ElderlySmartMedicationScheduleModule)
      },
      {
        path: 'medical-appointment',
        loadChildren: () => import('./medical-appointment/medical-appointment.module').then(m => m.ElderlySmartMedicalAppointmentModule)
      },
      {
        path: 'appointment-notification',
        loadChildren: () =>
          import('./appointment-notification/appointment-notification.module').then(m => m.ElderlySmartAppointmentNotificationModule)
      }
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ])
  ]
})
export class ElderlySmartEntityModule {}
