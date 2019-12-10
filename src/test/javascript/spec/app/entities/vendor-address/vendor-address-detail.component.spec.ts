import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ByteworkTestModule } from '../../../test.module';
import { VendorAddressDetailComponent } from 'app/entities/vendor-address/vendor-address-detail.component';
import { VendorAddress } from 'app/shared/model/vendor-address.model';

describe('Component Tests', () => {
  describe('VendorAddress Management Detail Component', () => {
    let comp: VendorAddressDetailComponent;
    let fixture: ComponentFixture<VendorAddressDetailComponent>;
    const route = ({ data: of({ vendorAddress: new VendorAddress(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ByteworkTestModule],
        declarations: [VendorAddressDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(VendorAddressDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(VendorAddressDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.vendorAddress).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
