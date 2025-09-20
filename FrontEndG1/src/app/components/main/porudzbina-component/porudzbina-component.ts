import { Component, OnInit } from '@angular/core';
import { MatTableDataSource, MatTableModule } from '@angular/material/table';
import { Porudzbina } from '../../../models/porudzbina';
import { PorudzbinaService } from '../../../services/porudzbina-service';
import { MatDialog } from '@angular/material/dialog';
import { MatIconModule } from '@angular/material/icon';
import { MatToolbarModule } from '@angular/material/toolbar';
import { CommonModule, DatePipe } from '@angular/common';
import { Dobavljac } from '../../../models/dobavljac';
import { PorudzbinaDialog } from '../../dialogs/porudzbina-dialog/porudzbina-dialog';
import { StavkaPorudzbineComponent } from '../stavka-porudzbine-component/stavka-porudzbine-component';

@Component({
  selector: 'app-porudzbina-component',
  imports: [
    MatTableModule,
    MatIconModule,
    MatToolbarModule,
    DatePipe,
    StavkaPorudzbineComponent,
    CommonModule,
  ],
  templateUrl: './porudzbina-component.html',
  styleUrl: './porudzbina-component.css',
})
export class PorudzbinaComponent implements OnInit {
  displayedColumns = ['id', 'datum', 'isporuceno', 'iznos', 'placeno', 'dobavljac', 'actions'];
  dataSource!: MatTableDataSource<Porudzbina>;
  parentSelectedPorudzbina!: Porudzbina;

  constructor(private porudzbinaService: PorudzbinaService, private dialog: MatDialog) {}

  ngOnInit(): void {
    this.loadData();
  }

  public loadData(): void {
    this.porudzbinaService.getAllPorudzbinas().subscribe({
      next: (data) => {
        console.log(data);
        this.dataSource = new MatTableDataSource<Porudzbina>(data);
      },
      error: (error) => {
        console.log(error.message);
      },
    });
  }

  public openDialog(
    flag: number,
    id?: number,
    datum?: Date,
    isporuceno?: Date,
    iznos?: number,
    placeno?: boolean,
    dobavljac?: Dobavljac
  ): void {
    const ref = this.dialog.open(PorudzbinaDialog, {
      data: { id, datum, isporuceno, iznos, placeno, dobavljac },
    });
    ref.componentInstance.flag = flag;
    ref.afterClosed().subscribe((response) => {
      if (response === 1) {
        this.ngOnInit();
      }
    });
  }

  public selectRow(row: Porudzbina): void {
    this.parentSelectedPorudzbina = row;
    // console.log(row);
  }
}
