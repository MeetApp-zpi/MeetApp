<script lang="ts">
    import { afterPageLoad } from '@roxi/routify';
    import execute from '../lib/fetchWrapper';
    import { userDetails, haveUnreadMessage } from '../lib/stores';

    execute('users/details', 'GET')
        .then((r) => (r.status === 200 ? r.json() : null))
        .then((r) => {
            if (r !== null) {
                $userDetails = r;
            }
        });

    $afterPageLoad((p) => {
        execute('chatrooms/haveUnreadMessage', 'GET')
            .then((r) => r.json())
            .then((r) => {
                $haveUnreadMessage = r;
                console.log(r);
            });
    });
</script>

<slot />
