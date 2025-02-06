package com.lmfm.api.translators;

import com.lmfm.api.dto.SubsectorRequest;
import com.lmfm.api.model.Sector;
import com.lmfm.api.model.Subsector;

public class SubsectorTranslator {

    public static Subsector fromDTO (SubsectorRequest subsectorRequest, Sector sector) {
        Subsector subsector = new Subsector();

        subsector.setNombre(subsectorRequest.getNombre());
        subsector.setId(subsectorRequest.getId());
        subsector.setSector(sector);

        return subsector;
    }

    public static SubsectorRequest toDTO (Subsector subsector) {
        SubsectorRequest request = new SubsectorRequest();

        request.setSectorId(subsector.getSector().getId());
        request.setId(subsector.getId());
        request.setNombre(subsector.getNombre());

        return request;
    }
}