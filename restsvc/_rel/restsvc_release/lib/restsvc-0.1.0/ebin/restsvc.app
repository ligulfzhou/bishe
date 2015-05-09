{application, restsvc, [
	{description, ""},
	{vsn, "0.1.0"},
	{id, ""},
	{modules, ['restsvc_app', 'restsvc_sup']},
	{registered, []},
	{applications, [
		kernel,
		stdlib,
		cowboy
	]},
	{mod, {restsvc_app, []}},
	{env, []}
]}.
