export interface IMedicationSchedule {
  id?: number;
  idMedicationSchedule?: number;
  meticationName?: string;
  elderly?: string;
  dose?: string;
  time?: string;
  state?: string;
}

export class MedicationSchedule implements IMedicationSchedule {
  constructor(
    public id?: number,
    public idMedicationSchedule?: number,
    public meticationName?: string,
    public elderly?: string,
    public dose?: string,
    public time?: string,
    public state?: string
  ) {}
}
