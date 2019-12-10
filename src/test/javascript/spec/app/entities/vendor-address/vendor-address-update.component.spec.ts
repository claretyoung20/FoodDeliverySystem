import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { ByteworkTestModule } from '../../../test.module';
import { VendorAddressUpdateComponent } from 'app/entities/vendor-address/vendor-address-update.component';
import { VendorAddressService } from 'app/entities/vendor-address/vendor-address.service';
import { VendorAddress } from 'app/shared/model/vendor-address.model';

describe('Component Tests', () => {
  describe('VendorAddress Management Update Component', () => {
    let comp: VendorAddressUpdateComponent;
    let fixture: ComponentFixture<VendorAddressUpdateComponent>;
    let service: VendorAddressService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ByteworkTestModule],
        declarations: [VendorAddressUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(VendorAddressUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(VendorAddressUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(VendorAddressService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new VendorAddress(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new VendorAddress();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
