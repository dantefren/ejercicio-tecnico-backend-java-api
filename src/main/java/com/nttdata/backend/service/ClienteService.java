package com.nttdata.backend.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.nttdata.backend.repository.ClienteRepository;
import com.nttdata.backend.model.Cliente;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

//DTACO: Control de errores
import com.nttdata.backend.exception.ServiceException;
import com.nttdata.backend.common.Error;

@Service
public class ClienteService {
    private final ClienteRepository clienteRepository;

    public ClienteService(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    //DTACO: C
    public Cliente createCliente(Cliente cliente) {
        return clienteRepository.save(cliente);
    }

    //DTACO: R
    public Optional<Cliente> getClienteById(UUID id) {
        return clienteRepository.findById(id);
    }

    public Optional<Cliente> getClienteByClienteId(String clienteid) {
        return clienteRepository.findByClienteid(clienteid);
    }

    public List<Cliente> getAllClientes() {
        return clienteRepository.findAll();
    }

    //DTACO: U
    @Transactional
    public Cliente updateCliente(UUID id, Cliente updatedCliente) {
        return clienteRepository.findById(id).map(cliente -> {
            cliente.setIdpersona(updatedCliente.getIdpersona());
            cliente.setClienteid(updatedCliente.getClienteid());
            cliente.setContrasenia(updatedCliente.getContrasenia());
            cliente.setEstado(updatedCliente.getEstado());
            return clienteRepository.save(cliente);
        }).orElseThrow(() ->  new ServiceException(Error.RECURSO_NO_ENCONTRADO));
    }

    //DTACO: D
    public void deleteCliente(UUID id) {
        clienteRepository.deleteById(id);
    }
}
