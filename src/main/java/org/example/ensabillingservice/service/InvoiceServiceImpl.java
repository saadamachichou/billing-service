package org.example.ensabillingservice.service;

import org.example.ensabillingservice.dto.InvoiceRequestDTO;
import org.example.ensabillingservice.dto.InvoiceResponseDTO;
import org.example.ensabillingservice.entities.Customer;
import org.example.ensabillingservice.entities.Invoice;
import org.example.ensabillingservice.mappers.InvoiceMapper;
import org.example.ensabillingservice.openfeign.CustomerRestClient;
import org.example.ensabillingservice.repository.InvoiceRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional

public class InvoiceServiceImpl implements InvoiceService {
    private  InvoiceRepository invoiceRepository;
    private InvoiceMapper invoiceMapper;
    private CustomerRestClient customerRestClient;
    public InvoiceServiceImpl(InvoiceRepository invoiceRepository, InvoiceMapper invoiceMapper, CustomerRestClient customerRestClient) {
        this.invoiceRepository = invoiceRepository;
        this.invoiceMapper = invoiceMapper;
        this.customerRestClient = customerRestClient;
    }


    @Override
    public InvoiceResponseDTO save(InvoiceRequestDTO invoiceRequestDTO) {

        Invoice invoice=invoiceMapper.fromInvoiceRequestDTO(invoiceRequestDTO);
        invoice.setDate(new Date());
        invoice.setId(UUID.randomUUID().toString());
        Invoice saveInvoice=invoiceRepository.save(invoice);
        return invoiceMapper.fromInvoice(saveInvoice);
    }

    @Override
    public InvoiceResponseDTO getInvoice(String invoiceId) {
        Invoice invoice=invoiceRepository.findById(invoiceId).get();
        Customer customer=customerRestClient.getCustomer(invoice.getCustomerId());
        invoice.setCustomer(customer);
        return invoiceMapper.fromInvoice(invoice);
    }



    @Override
    public List<InvoiceResponseDTO> invoicesByCustomerId(String customerId) {
        List<Invoice> invoices=invoiceRepository.findByCustomerId(customerId);
        for(Invoice invoice:invoices) {
            Customer customer = customerRestClient.getCustomer(invoice.getCustomerId());
            invoice.setCustomer(customer);
        }
        return invoices.stream()
                     .map(invoice->invoiceMapper.fromInvoice(invoice))
                    .collect(Collectors.toList());
    }
    @Override
    public void deleteInvoice(String id) {
        invoiceRepository.deleteById(id);
    }

    @Override
    public List<InvoiceResponseDTO> allInvoices() {
        List<Invoice> invoices= invoiceRepository.findAll();
       for(Invoice invoice:invoices) {
            Customer customer = customerRestClient.getCustomer(invoice.getCustomerId());
            invoice.setCustomer(customer);
        }
        return invoices.stream()
                .map(inv->invoiceMapper.fromInvoice(inv))
                .collect(Collectors.toList());
    }
}
/*
   @Override
    public List<InvoiceResponseDTO> allInvoices() {
        List<Invoice> invoices= invoiceRepository.findAll();
       for(Invoice invoice:invoices) {
           Customer customer = customerRestClient.getCustomer(invoice.getCustomerId());
           invoice.setCustomer(customer);

           return invoices.stream().map(inv -> invoiceMapper.fromInvoice(inv)).collect(Collectors.toList());
       }
    } */

