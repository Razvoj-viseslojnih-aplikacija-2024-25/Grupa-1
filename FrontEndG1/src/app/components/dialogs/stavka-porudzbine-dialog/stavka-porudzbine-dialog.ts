import { Component, Inject } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogModule, MatDialogRef } from '@angular/material/dialog';
import { MatSnackBar } from '@angular/material/snack-bar';
import { StavkaPorudzbineService } from '../../../services/stavka-porudzbine-service';
import { StavkaPorudzbine } from '../../../models/stavka-porudzbine';
import { Artikl } from '../../../models/artikl';
import { ArtiklService } from '../../../services/artikl-service';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatSelectModule } from '@angular/material/select';
import { CommonModule } from '@angular/common';
import { MatButtonModule } from '@angular/material/button';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-stavka-porudzbine-dialog',
  imports: [
    MatFormFieldModule,
    MatInputModule,
    MatSelectModule,
    CommonModule,
    MatDialogModule,
    MatButtonModule,
    FormsModule,
  ],
  templateUrl: './stavka-porudzbine-dialog.html',
  styleUrl: './stavka-porudzbine-dialog.css',
})
export class StavkaPorudzbineDialog {
  flag!: number;
  artikli!: Artikl[];

  constructor(
    private snackBar: MatSnackBar,
    private dialogRef: MatDialogRef<StavkaPorudzbineDialog>,
    private stavkaPorudzbineService: StavkaPorudzbineService,
    @Inject(MAT_DIALOG_DATA) public data: StavkaPorudzbine,
    private artiklService: ArtiklService
  ) {}

  ngOnInit(): void {
    this.artiklService.getAllArtikls().subscribe((data) => {
      this.artikli = data;
    });
  }

  public add(): void {
    this.stavkaPorudzbineService.createStavkaPorudzbine(this.data).subscribe({
      next: (data) => {
        this.dialogRef.close(1);
        this.snackBar.open(
          `Stavka porudzbine with naziv: ${data.id} has been successfully created!`,
          'Okay',
          { duration: 2500 }
        );
      },
      error: (error) => {
        this.snackBar.open(
          'There was an issue while creating a new instance of Stavka Porudzbine',
          'Okay',
          { duration: 2500 }
        );
        console.log(error.message);
      },
    });
  }

  public update(): void {
    this.stavkaPorudzbineService.updateStavkaPorudzbine(this.data).subscribe({
      next: (data) => {
        this.dialogRef.close(1);
        this.snackBar.open(
          `Stavka porudzbine with naziv: ${data.id} has been successfully updated!`,
          'Okay',
          { duration: 2500 }
        );
      },
      error: (error) => {
        this.snackBar.open(
          'There was an issue while updating a new instance of Stavka Porudzbine',
          'Okay',
          { duration: 2500 }
        );
        console.log(error.message);
      },
    });
  }

  public delete(): void {
    this.stavkaPorudzbineService.deleteStavkaPorudzbine(this.data.id).subscribe({
      next: (response) => {
        this.dialogRef.close(1);
        this.snackBar.open(response, 'Okay', { duration: 2500 });
      },
      error: (error) => {
        this.snackBar.open(
          'There was an issue while deleting selected instance of Stavka Porudzbine',
          'Okay',
          { duration: 2500 }
        );
        console.log(error.message);
      },
    });
  }

  public cancel(): void {
    this.dialogRef.close();
    this.snackBar.open('You have given up on changes', 'Okay', { duration: 2500 });
  }

  public compare(a: StavkaPorudzbine, b: StavkaPorudzbine) {
    return a.id == b.id;
  }
}
