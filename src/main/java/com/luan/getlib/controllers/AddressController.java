package com.luan.getlib.controllers;

import com.luan.getlib.models.Address;
import com.luan.getlib.models.Result;
import com.luan.getlib.services.RestCountriesService;
import com.luan.getlib.services.ZipCodeService;
import com.luan.getlib.utils.DataFormatter;

public class AddressController {
    private final ZipCodeService zipCodeSearcher = new ZipCodeService();
    private final RestCountriesService currencySearcher = new RestCountriesService();

    private final String ZIP_CODE_NOT_FOUND = "Não foi possível auto-preencher o endereço!";
    private final String SUCCESS = "Endereço encontrado!";

    public Result<Address> findAddressByZipCode(String zipCode) {
        zipCode = DataFormatter.removeNonNumbers(zipCode);
        Address address = zipCodeSearcher.getAddressByZipCode(zipCode);
        if(address.isEmpty())
            return new Result<>(false, ZIP_CODE_NOT_FOUND, address);

        return new Result<>(true, SUCCESS, address);
    }

    public Result<String> findAddressByCountry(String country) {
        return currencySearcher.getCurrencyByCountry(country);
    }
}
