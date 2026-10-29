package jsf;

import jpa.entities.Reserva;
import jpa.entities.Habitacion;
import jpa.entities.Mascota;
import jpa.entities.Usuario;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import jsf.util.JsfUtil;
import jsf.util.PaginationHelper;
import jpa.sessions.ReservaFacade;

import java.io.Serializable;
import java.util.ResourceBundle;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.faces.model.SelectItem;

@Named("reservaController")
@SessionScoped
public class ReservaController implements Serializable {

    private Reserva current;
    private DataModel items = null;
    @EJB
    private jpa.sessions.ReservaFacade ejbFacade;
    private PaginationHelper pagination;
    private int selectedItemIndex;
    // Cliente elegido en el formulario; solo sirve para filtrar las mascotas (no se guarda en Reserva)
    private Usuario usuarioSeleccionado;

    public Usuario getUsuarioSeleccionado() {
        return usuarioSeleccionado;
    }

    public void setUsuarioSeleccionado(Usuario usuarioSeleccionado) {
        this.usuarioSeleccionado = usuarioSeleccionado;
    }

    public ReservaController() {
    }

    public Reserva getSelected() {
        if (current == null) {
            current = new Reserva();
            selectedItemIndex = -1;
        }
        return current;
    }

    private ReservaFacade getFacade() {
        return ejbFacade;
    }

    public PaginationHelper getPagination() {
        if (pagination == null) {
            pagination = new PaginationHelper(10) {

                @Override
                public int getItemsCount() {
                    return getFacade().count();
                }

                @Override
                public DataModel createPageDataModel() {
                    return new ListDataModel(getFacade().findRange(new int[]{getPageFirstItem(), getPageFirstItem() + getPageSize()}));
                }
            };
        }
        return pagination;
    }

    public String prepareList() {
        recreateModel();
        return "List";
    }

    public String prepareView() {
        current = (Reserva) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "View";
    }

    public String prepareCreate() {
        current = new Reserva();
            usuarioSeleccionado = null; // limpia el cliente del formulario anterior
        selectedItemIndex = -1;
        return "Create";
    }

        public String create() {
        try {
            ResourceBundle bundle = ResourceBundle.getBundle("/resources/Bundle");
            Date ingreso = current.getFechaIngreso();
            Date salida = current.getFechaSalida();

            // VALIDACION 1: la salida debe ser posterior al ingreso
            if (ingreso == null || salida == null || !salida.after(ingreso)) {
                JsfUtil.addErrorMessage(bundle.getString("ReservaFechasInvalidas"));
                return null; // se queda en el formulario
            }

            // VALIDACION 2: disponibilidad (cuenta reservas de esa habitacion que se cruzan)
            if (getFacade().contarCruces(current.getIdHabitacion(), ingreso, salida) > 0) {
                JsfUtil.addErrorMessage(bundle.getString("ReservaNoDisponible"));
                return null; // no se guarda
            }

            // Estado inicial de la reserva
            current.setEstado(bundle.getString("ReservaEstadoInicial"));

            // Total = noches * precio por noche de la habitacion
            Habitacion hab = current.getIdHabitacion();
            long msPorDia = 24L * 60 * 60 * 1000;
            long noches = Math.round((salida.getTime() - ingreso.getTime()) / (double) msPorDia);
            if (hab.getPrecioNoche() != null) {
                current.setTotal(hab.getPrecioNoche().multiply(BigDecimal.valueOf(noches)));
            }

            getFacade().create(current);
            JsfUtil.addSuccessMessage(bundle.getString("ReservaCreated"));
            recreatePagination();
            recreateModel();
            return prepareCreate();
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/resources/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String prepareEdit() {
        current = (Reserva) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "Edit";
    }

    public String update() {
        try {
            getFacade().edit(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/resources/Bundle").getString("ReservaUpdated"));
            return "View";
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/resources/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String destroy() {
        current = (Reserva) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        performDestroy();
        recreatePagination();
        recreateModel();
        return "List";
    }

    public String destroyAndView() {
        performDestroy();
        recreateModel();
        updateCurrentItem();
        if (selectedItemIndex >= 0) {
            return "View";
        } else {
            // all items were removed - go back to list
            recreateModel();
            return "List";
        }
    }

    private void performDestroy() {
        try {
            getFacade().remove(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/resources/Bundle").getString("ReservaDeleted"));
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/resources/Bundle").getString("PersistenceErrorOccured"));
        }
    }

    private void updateCurrentItem() {
        int count = getFacade().count();
        if (selectedItemIndex >= count) {
            // selected index cannot be bigger than number of items:
            selectedItemIndex = count - 1;
            // go to previous page if last page disappeared:
            if (pagination.getPageFirstItem() >= count) {
                pagination.previousPage();
            }
        }
        if (selectedItemIndex >= 0) {
            current = getFacade().findRange(new int[]{selectedItemIndex, selectedItemIndex + 1}).get(0);
        }
    }

    public DataModel getItems() {
        if (items == null) {
            items = getPagination().createPageDataModel();
        }
        return items;
    }

    private void recreateModel() {
        items = null;
    }

    private void recreatePagination() {
        pagination = null;
    }

    public String next() {
        getPagination().nextPage();
        recreateModel();
        return "List";
    }

    public String previous() {
        getPagination().previousPage();
        recreateModel();
        return "List";
    }

    public SelectItem[] getItemsAvailableSelectMany() {
        return JsfUtil.getSelectItems(ejbFacade.findAll(), false);
    }

    public SelectItem[] getItemsAvailableSelectOne() {
        return JsfUtil.getSelectItems(ejbFacade.findAll(), true);
    }
        // Mascotas del cliente elegido (alimenta el menu "idMascota" del formulario).
    // Si aun no hay cliente, solo aparece la opcion "---".
    public SelectItem[] getMascotasDelUsuario() {
        if (usuarioSeleccionado == null || usuarioSeleccionado.getMascotaCollection() == null) {
            return JsfUtil.getSelectItems(new ArrayList<Mascota>(), true);
        }
        return JsfUtil.getSelectItems(new ArrayList<Mascota>(usuarioSeleccionado.getMascotaCollection()), true);
    }
    public Reserva getReserva(java.lang.Integer id) {
        return ejbFacade.find(id);
    }

    @FacesConverter(forClass = Reserva.class)
    public static class ReservaControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            ReservaController controller = (ReservaController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "reservaController");
            return controller.getReserva(getKey(value));
        }

        java.lang.Integer getKey(String value) {
            java.lang.Integer key;
            key = Integer.valueOf(value);
            return key;
        }

        String getStringKey(java.lang.Integer value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value);
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof Reserva) {
                Reserva o = (Reserva) object;
                return getStringKey(o.getIdReserva());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + Reserva.class.getName());
            }
        }

    }

}
