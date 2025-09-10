import { Component, OnInit } from '@angular/core';
import { MatTableDataSource, MatTableModule } from '@angular/material/table';
import { MatIconModule } from '@angular/material/icon';
import { MatToolbarModule } from '@angular/material/toolbar';
import { Dobavljac } from '../../../models/dobavljac';
import { DobavljacService } from '../../../services/dobavljac-service';
import { MatDialog } from '@angular/material/dialog';
import { DobavljacDialog } from '../../dialogs/dobavljac-dialog/dobavljac-dialog';

@Component({
  selector: 'app-dobavljac-component',
  imports: [MatTableModule, MatIconModule, MatToolbarModule],
  templateUrl: './dobavljac-component.html',
  styleUrl: './dobavljac-component.css',
})
export class DobavljacComponent implements OnInit {
  displayedColumns = ['id', 'naziv', 'kontakt', 'adresa', 'actions'];
  dataSource!: MatTableDataSource<Dobavljac>;

  constructor(private dobavljacService: DobavljacService, private dialog: MatDialog) {}

  ngOnInit(): void {
    this.loadData();
  }

  public loadData(): void {
    this.dobavljacService.getAllDobavljacs().subscribe({
      next: (data) => {
        console.log(data);
        this.dataSource = new MatTableDataSource<Dobavljac>(data);
      },
      error: (error) => {
        console.log(error.message);
      },
    });
  }

  public openDialog(
    flag: number,
    id?: number,
    naziv?: String,
    kontakt?: String,
    adresa?: String
  ): void {
    const ref = this.dialog.open(DobavljacDialog, { data: { id, naziv, kontakt, adresa } });
    ref.componentInstance.flag = flag;
    ref.afterClosed().subscribe((response) => {
      if (response === 1) {
        this.ngOnInit();
      }
    });
  }
}
