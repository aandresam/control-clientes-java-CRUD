<section id="actions" class="py-4 mb-4 bg-light">
    <div class="container">
        <div class="row">
            <div class="col-md-3 d-grid gap-3 mb-2">
                <a href="index.jsp" class="btn btn-light btn-block">
                    <i class="fas fa-arrow-left"></i>
                    Regresar al inicio
                </a>
            </div>
            <div class="col-md-3 d-grid gap-3 mb-2">
                <button type="submit" class="btn btn-success btn-block">
                    <i class="fa-solid fa-check"></i>
                    Guardar información
                </button>
            </div>
            <div class="col-md-3 d-grid gap-3 mb-2">
                <a href="${pageContext.request.contextPath}/ServletControlador?accion=delete&idCliente=${cliente.idCliente}"
                   class="btn btn-danger ">
                    <i class="fas fa-trash"></i> Eliminar Cliente
                </a>
            </div>
        </div>
    </div>
</section>