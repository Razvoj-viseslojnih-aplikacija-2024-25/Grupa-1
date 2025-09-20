import { Component, Input, OnChanges, OnInit, SimpleChanges } from '@angular/core';
import { MatTableDataSource, MatTableModule } from '@angular/material/table';
import { StavkaPorudzbine } from '../../../models/stavka-porudzbine';
import { StavkaPorudzbineService } from '../../../services/stavka-porudzbine-service';
import { MatDialog } from '@angular/material/dialog';
import { Artikl } from '../../../models/artikl';
import { StavkaPorudzbineDialog } from '../../dialogs/stavka-porudzbine-dialog/stavka-porudzbine-dialog';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatIconModule } from '@angular/material/icon';
import { Porudzbina } from '../../../models/porudzbina';

@Component({
  selector: 'app-stavka-porudzbine',
  imports: [MatTableModule, MatIconModule, MatToolbarModule],
  templateUrl: './stavka-porudzbine-component.html',
  styleUrl: './stavka-porudzbine-component.css',
})
export class StavkaPorudzbineComponent implements OnChanges, OnInit {
  displayedColumns = ['id', 'redniBroj', 'kolicina', 'jedinicaMere', 'cena', 'artikl', 'actions'];
  dataSource!: MatTableDataSource<StavkaPorudzbine>;

  @Input()
  childSelectedPorudzbina!: Porudzbina;

  constructor(
    private stavkaPorudzbineService: StavkaPorudzbineService,
    private dialog: MatDialog
  ) {}

  ngOnChanges(changes: SimpleChanges): void {
    this.loadData();
  }

  ngOnInit(): void {
    this.loadData();
  }

  public loadData(): void {
    this.stavkaPorudzbineService.getStavkeByPorudzbina(this.childSelectedPorudzbina.id).subscribe({
      next: (data) => {
        console.log(data);
        this.dataSource = new MatTableDataSource<StavkaPorudzbine>(data);
      },
      error: (error) => {
        console.log(error.message);
      },
    });
  }

  public openDialog(
    flag: number,
    id?: number,
    redniBroj?: number,
    kolicina?: number,
    jedinicaMere?: String,
    cena?: number,
    artikl?: Artikl
  ): void {
    const ref = this.dialog.open(StavkaPorudzbineDialog, {
      data: { id, redniBroj, kolicina, jedinicaMere, cena, artikl },
    });
    ref.componentInstance.flag = flag;
    ref.componentInstance.data.porudzbina = this.childSelectedPorudzbina;
    ref.afterClosed().subscribe((response) => {
      if (response === 1) {
        this.ngOnInit();
      }
    });
  }
}
