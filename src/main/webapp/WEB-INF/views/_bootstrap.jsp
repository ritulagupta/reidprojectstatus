<jsp:useBean id="mapper" class="com.fasterxml.jackson.databind.ObjectMapper"  />

<script type="text/javascript">
var bootstrap = <%= mapper.writeValueAsString(request.getAttribute("bootstrap"))%>;
</script>